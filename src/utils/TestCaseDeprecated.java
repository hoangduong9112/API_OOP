package utils;

public class TestCaseDeprecated<T> {
    private final T params;
    private final String testDescription;
    private final String codeExpectation;
    private final String messageExpectation;

    public TestCaseDeprecated(String codeExpectation, String messageExpectation, String testDescription, T params) {
        this.codeExpectation = codeExpectation;
        this.messageExpectation = messageExpectation;
        this.testDescription = testDescription;
        this.params = params;
    }

    public T getParams() {
        return params;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public String getCodeExpectation() {
        return codeExpectation;
    }

    public String getMessageExpectation() {
        return messageExpectation;
    }

}
