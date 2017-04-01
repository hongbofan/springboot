package com.example.annotation;

import com.example.util.RedisUtil;
import com.example.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by yfmacmini001 on 2017/4/1.
 */
@Aspect
@Component
public class CacheAspect {
    // 获取被拦截方法参数名列表(使用Spring支持类库)
    private final LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
    // 使用SPEL进行key的解析
    private final ExpressionParser parser = new SpelExpressionParser();
    // SPEL上下文
    private final StandardEvaluationContext context = new StandardEvaluationContext();

    @Autowired
    private RedisUtil redisCache;

    @Pointcut("@annotation(com.example.annotation.RedisLock)")
    public void redisLock() {
    }

    @Around("redisLock()")
    public Object redisLock(ProceedingJoinPoint pjp) {
        Object result = null;
        Method method = getMethod(pjp);
        RedisLock redisLock = method.getAnnotation(RedisLock.class);
        String fieldKey = parseKey(redisLock.fieldKey(), method, pjp.getArgs());
        if (StringUtils.isNotBlank(fieldKey)) {
            if (redisCache.setnx(redisLock.key() + fieldKey, redisLock.value()) == 0) {
                return Result.error("请不要重复请求");
            }
            redisCache.expire(redisLock.key() + fieldKey, redisLock.expireTime());
        }
        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }

    public Method getMethod(ProceedingJoinPoint pjp) {
        // 获取参数的类型
        Object[] args = pjp.getArgs();
        Class[] argTypes = new Class[pjp.getArgs().length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
        Method method = null;
        try {
            method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), argTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return method;

    }
    private String parseKey(String key, Method method, Object[] args) {

        String[] paraNameArr = u.getParameterNames(method);

        // 把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context, String.class);
    }
}
