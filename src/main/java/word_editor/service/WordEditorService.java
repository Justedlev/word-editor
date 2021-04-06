package word_editor.service;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class WordEditorService implements IWordEditor {

    private int index;
    private List<StringBuilder> text;

    public WordEditorService() {
        this.index = -1;
        this.text = new LinkedList<>();
    }

    @Override
    public void add(String s) {
        if (s == null || s.isEmpty())
            return;
        if(this.index >= 0) {
            add(s, this.text.get(this.index).length());
            return;
        }
        addToTextList(s);
    }

    private void addToTextList(String s) {
        StringBuilder sb = new StringBuilder(s);
        this.text.add(sb);
        this.index += 1;
    }

    private StringBuilder getCopyOfLastText() {
        return new StringBuilder(this.text.get(this.index));
    }

    @Override
    public void add(String s, int position) {
        if (this.index < 0 || position < 0) {
            addToTextList(s);
            return;
        }
        if(position > this.text.get(this.index).length())
            return;
        StringBuilder sb = getCopyOfLastText();
        sb.insert(position, s);
        addToTextList(sb.toString());
    }

    @Override
    public void remove(int fromPosition, int toPosition) {
        if (this.index < 0 || (fromPosition < 0 || toPosition < 0 || fromPosition >= toPosition))
            return;
        int index = this.text.get(this.index).length();
        if(index < fromPosition || index < toPosition)
            return;
        StringBuilder sb = getCopyOfLastText();
        sb.delete(fromPosition, toPosition);
        addToTextList(sb.toString());
    }

    @Override
    public void italic(int fromPosition, int toPosition) {
        if (this.index < 0 || (fromPosition < 0 || toPosition < 0 || fromPosition > toPosition))
            return;
        int index = this.text.get(this.index).length();
        if(index < fromPosition || index < toPosition)
            return;
        StringBuilder sb = getCopyOfLastText();
        String result = "<i>" + sb.substring(fromPosition, toPosition) + "</i>";
        sb.replace(fromPosition, toPosition, result);
        addToTextList(sb.toString());
    }

    @Override
    public void bold(int fromPosition, int toPosition) {
        if (this.index < 0 || (fromPosition < 0 || toPosition < 0 || fromPosition > toPosition))
            return;
        int index = this.text.get(this.index).length();
        if(index < fromPosition || index < toPosition)
            return;
        StringBuilder sb = getCopyOfLastText();
        String result = "<b>" + sb.substring(fromPosition, toPosition) + "</b>";
        sb.replace(fromPosition, toPosition, result);
        addToTextList(sb.toString());
    }

    @Override
    public void underline(int fromPosition, int toPosition) {
        if (this.index < 0 || (fromPosition < 0 || toPosition < 0 || fromPosition > toPosition))
            return;
        int index = this.text.get(this.index).length();
        if(index < fromPosition || index < toPosition)
            return;
        StringBuilder sb = getCopyOfLastText();
        String result = "<u>" + sb.substring(fromPosition, toPosition) + "</u>";
        sb.replace(fromPosition, toPosition, result);
        addToTextList(sb.toString());
    }

    @Override
    public void redo() {
        this.index += 1;
    }

    @Override
    public void undo() {
        this.index -= 1;
    }

    @Override
    public String print() {
        if(this.index < 0)
            return null;
        if (this.index >= this.text.size())
            return this.text.get(this.text.size() - 1).toString();
        return this.text.get(this.index).toString();
    }
}
