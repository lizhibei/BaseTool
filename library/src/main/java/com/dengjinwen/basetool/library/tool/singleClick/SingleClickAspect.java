package com.dengjinwen.basetool.library.tool.singleClick;

import android.view.View;

import com.dengjinwen.basetool.library.tool.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class SingleClickAspect {
      private static final long DEFAULT_TIME_INTERVAL = 5000;

      /**
       * 定义切点，标记切点为所有被@SingleClick注解的方法
       */
      @Pointcut("execution(@com.dengjinwen.basetool.library.tool.singleClick.MySingleClick * *(..))")
      public void addSingleClick() {
      }

      /**
       * 定义一个切面方法，包裹切点方法
       */
      @Around("addSingleClick()")
      public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
            // 取出方法的参数
            log.e("执行了快速点击注解");
            View view = null;
            for (Object arg : joinPoint.getArgs()) {
                  if (arg instanceof View) {
                        view = (View) arg;
                        break;
                  }
            }
            if (view == null) {
                  log.e("没有传入view");
                  return;
            }
            // 取出方法的注解
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();

            if (!method.isAnnotationPresent(MySingleClick.class)) {
                  return;
            }
            MySingleClick singleClick = method.getAnnotation(MySingleClick.class);
            // 判断是否快速点击
            if (!XClickUtil.isFastDoubleClick(view, singleClick.value())) {
                  // 不是快速点击，执行原方法
                  joinPoint.proceed();
            }
      }
}
