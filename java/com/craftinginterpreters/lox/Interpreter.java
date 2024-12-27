package com.craftinginterpreters.lox;

class Interpreter implements Expr.Visitor<Object> {
    @Override
    public Object visitLiteralExpr(Expr.Binary expr) {
        // Implementation of binary expression evaluation
        // and return the result
        return expr.value;
    }
    @Override
    public Object visitGroupingExpr(Expr.Grouping expr) {
        return evaluate(expr.expression);
    }

    private Object evaluate(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public Object visitBinaryExpr(Expr.Binary expr) {
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);

        switch (expr.operator.type) {
            case GREATER:
                checkNumberOperands(expr.operator, left, right);
                return (double)left > (double)right;
            case GREATER_EQUAL:
                checkNumberOperands(expr.operator, left, right);
                return (double)left >= (double)right;
            case LESS:
                checkNumberOperands(expr.operator, left, right);
                return (double)left < (double)right;
            case LESS_EQUAL:
                checkNumberOperands(expr.operator, left, right);
                return (double)left <= (double)right;
            case PLUS:
                checkNumberOperands(expr.operator, left, right);
                if (left instanceof Double && right instanceof Double) {
                    return (double) left + (double) right;
                }
                if (left instanceof String && right instanceof String) {
                    return (String) left + (String) right;
                }
                throw new RuntimeException("Operands must two numbers of two strings.");
            case MINUS:
                checkNumberOperands(expr.operator, left, right);
                return (double) left - (double) right;
            case STAR:
                checkNumberOperands(expr.operator, left, right);
                return (double) left * (double) right;
            case SLASH:
                checkNumberOperands(expr.operator, left, right);
                if ((double) right!= 0) {
                    return (double) left / (double) right;
                }
                throw new RuntimeException("Division by zero.");
            case BANG_EQUAL:
                return !isEqual(left, right);
            case EQUAL_EQUAL:
                return isEqual(left, right);
        }

        return null; // TOD
    }

    @Override
    public Object visitUnaryExpr(Expr.Unary expr) {
        // Implementation of unary expression evaluation
        // and return the result
        Object right = evaluate(expr.expression);

        switch (expr.operator.type) {
            case BANG:
                return !isTruthy(right);
            case MINUS:
            checkNumberOperand(expr.operator, right);
            return -(double)right;
        }

        return null; // TOD
    }

    // For unary operators
    private void checkNumberOperand(Token operator, Object operand) {
        if (!(operand instanceof Double)) {
            throw new RuntimeError(operator, "Operand must be a number.");
        }
    }

    // For binary operators
    private void checkNumberOperands(Token operator, Object left, Object right) {
        if (left instanceof Double && right instanceof Double) return;
        throw new RuntimeError(operator, "Operands must be numbers.");
    }

    private boolean isTruthy(Object value) {
        if (value == null) return false;
        if (value instanceof Boolean) return (boolean) value;
        return true;
    }

    private boolean isEqual(Object a, Object b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.equals(b);
    }
}
