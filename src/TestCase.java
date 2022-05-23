public class TestCase<T> {
    private T params;
    private String testDescription;
    private String codeExpectation;
    private String messageExpectation;

    public TestCase(String codeExpectation, String messageExpectation, String testDescription, T params) {
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
