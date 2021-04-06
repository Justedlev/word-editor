package word_editor.service;

public interface IWordEditor {
    void add(String s);
    void add(String s, int position);
    void remove(int fromPosition, int toPosition);
    void italic(int fromPosition, int toPosition);
    void bold(int fromPosition, int toPosition);
    void underline(int fromPosition, int toPosition);
    void redo();
    void undo();
    String print();
}
