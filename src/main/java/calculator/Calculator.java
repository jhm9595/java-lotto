package calculator;

import data.Messages;

import java.util.Optional;
import java.util.function.Consumer;

public class Calculator {

    enum SingEnum {
        ADD("+", Calculator::add),
        SUBTRACT("-", Calculator::subtract),
        MULTIPLY("*", Calculator::multiply),
        DIVIDE("/", Calculator::divide);;
        private final String sign;
        private final Consumer<Integer> consumer;

        SingEnum(String sign, Consumer<Integer> consumer) {
            this.sign = sign;
            this.consumer = consumer;
        }
    }

    private static int result = 0;
    private final Formula formula;

    Calculator(int init) {
        this(init, "0");
    }

    public Calculator(String formula) {
        this(0, formula);
    }

    public Calculator(int init, String formula) {
        this.result = init;
        this.formula = new Formula(formula);
    }

    public int getResult() {
        return result;
    }

    public static int add(int num) {
        return result += num;
    }

    public static int subtract(int num) {
        return result -= num;
    }

    public static int multiply(int num) {
        return result *= num;
    }

    public static int divide(int num) {
        if (result % num != 0) {
            throw new IllegalArgumentException(Messages.TYPE_ERROR);
        }
        return result /= num;
    }

    public void calculate(String sign, String numStr) {
        Optional.of(SingEnum.valueOf(sign))
                .orElseThrow(() -> new IllegalArgumentException(Messages.TYPE_ERROR))
                .consumer.accept(Integer.parseInt(numStr));
    }

    public int calculateFormula() {
        String[] calTargetArr = this.formula.getCalculateTarget();
        for (int i = 0; i < calTargetArr.length / 2; i++) {
            this.calculate(calTargetArr[i * 2], calTargetArr[i * 2 + 1]);
        }
        return this.getResult();
    }
}