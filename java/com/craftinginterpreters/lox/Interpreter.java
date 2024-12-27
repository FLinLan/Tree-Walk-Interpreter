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
                return (double)left > (double)right;
            case GREATER_EQUAL:
                return (double)left >= (double)right;
            case LESS:
                return (double)left < (double)right;
            case LESS_EQUAL:
                return (double)left <= (double)right;
            case PLUS:
                if (left instanceof Double && right instanceof Double) {
                    return (double) left + (double) right;
                }
                throw new RuntimeException("Operands must be numbers.");
            case MINUS:
                return (double) left - (double) right;
            case STAR:
                return (double) left * (double) right;
            case SLASH:
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

    private void checkNumberOperand(Token operator, Object operand) {
        if (!(operand instanceof Double)) {
            throw new RuntimeError(operator, "Operand must be a number.");
        }
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