package com.craftinginterpreters.lox;

import com.craftinginterpreters.lox.Expr.Binary;
import com.craftinginterpreters.lox.Expr.Grouping;
import com.craftinginterpreters.lox.Expr.Literal;
import com.craftinginterpreters.lox.Expr.Unary;

public class RpnPrinter implements Expr.Visitor<String> {

	@Override
	public String visitBinaryExpr(Binary expr) {
		return expr.left.accept(this) + " " + expr.right.accept(this) + " " + expr.operator.lexeme;
	}

	@Override
	public String visitGroupingExpr(Grouping expr) {
		return expr.expression.accept(this);
	}

	@Override
	public String visitLiteralExpr(Literal expr) {
		if (expr.value == null)
			return "nil";
		return expr.value.toString();
	}

	@Override
	public String visitUnaryExpr(Unary expr) {
		return expr.right.accept(this) + " " + expr.operator.lexeme;
	}

	public static void main(String... args) {
		Expr expression = new Expr.Binary(
				new Expr.Grouping(new Expr.Binary(new Expr.Literal(1), new Token(TokenType.PLUS, "+", null, 1),
						new Expr.Literal(2))),
				new Token(TokenType.STAR, "*", null, 1), new Expr.Grouping(new Expr.Binary(new Expr.Literal(4),
						new Token(TokenType.MINUS, "-", null, 1), new Expr.Literal(3))));

		System.out.println(new RpnPrinter().print(expression));

	}

	private String print(Expr expression) {
		return expression.accept(this);
	}
}
