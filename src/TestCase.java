public class TestCase<ParamsType> {
    ParamsType params;
    String testDescription;
    String codeExpectation;
    String messageExpectation;

    public TestCase(String codeExpectation, String messageExpectation, String testDescription, ParamsType params) {
        this.codeExpectation = codeExpectation;
        this.messageExpectation = messageExpectation;
        this.testDescription = testDescription;
        this.params = params;
    }
}
