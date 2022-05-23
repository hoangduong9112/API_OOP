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

    public void setParams(T params) {
        this.params = params;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    public String getCodeExpectation() {
        return codeExpectation;
    }

    public void setCodeExpectation(String codeExpectation) {
        this.codeExpectation = codeExpectation;
    }

    public String getMessageExpectation() {
        return messageExpectation;
    }

    public void setMessageExpectation(String messageExpectation) {
        this.messageExpectation = messageExpectation;
    }
}
