package Command;

public class DeleteInfo {
    private String deletedContent;
    private int deletedLineNumber;

    public DeleteInfo(String deletedContent, int deletedLineNumber) {
        this.deletedContent = deletedContent;
        this.deletedLineNumber = deletedLineNumber;
    }

    public String getDeletedContent() {
        return deletedContent;
    }

    public int getDeletedLineNumber() {
        return deletedLineNumber;
    }
}
