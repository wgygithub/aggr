package org.example.resolver;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author admin
 */
@Component
public class LockKeyResolver {

  private final SpelExpressionParser parser = new SpelExpressionParser();

  private final Map<String, Expression> expressionCache = new HashMap<>();

  /**
   * 根据 AOP 切点和表达式解析锁键
   *
   * @param joinPoint     AOP 切点
   * @param keyExpression 锁键表达式
   * @return 解析后的锁键
   */
  public String resolveKey(ProceedingJoinPoint joinPoint, String keyExpression) {
    if (keyExpression == null || keyExpression.isEmpty()) {
      throw new IllegalArgumentException("锁键表达式不能为空");
    }
    // 获取方法签名和参数信息
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    String[] paramNames = signature.getParameterNames();
    if (paramNames == null || paramNames.length == 0) {
      throw new IllegalArgumentException("方法参数名无法解析，请确保编译时启用了 -parameters 选项");
    }

    // 构建上下文
    EvaluationContext context = buildEvaluationContext(joinPoint, paramNames);

    // 缓存并解析表达式
    Expression expression = getExpressionFromCache(keyExpression);
    return expression.getValue(context, String.class);
  }

  /**
   * 构建 SpEL 上下文
   *
   * @param joinPoint  AOP 切点
   * @param paramNames 方法参数名数组
   * @return SpEL 上下文
   */
  private EvaluationContext buildEvaluationContext(ProceedingJoinPoint joinPoint, String[] paramNames) {
    StandardEvaluationContext context = new StandardEvaluationContext();
    Object[] args = joinPoint.getArgs();

    if (args.length != paramNames.length) {
      throw new IllegalArgumentException("方法参数数量与参数名数量不一致");
    }

    for (int i = 0; i < args.length; i++) {
      context.setVariable(paramNames[i], args[i]);
    }

    return context;
  }

  /**
   * 从缓存中获取或解析表达式
   *
   * @param keyExpression 锁键表达式
   * @return 解析后的表达式
   */
  private Expression getExpressionFromCache(String keyExpression) {
    return expressionCache.computeIfAbsent(keyExpression, expr -> {
      try {
        return parser.parseExpression(expr);
      } catch (Exception e) {
        throw new IllegalArgumentException("锁键表达式解析失败: " + expr, e);
      }
    });
  }
}
