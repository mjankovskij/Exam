package lt.codeacademy.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lt.codeacademy.interfaces.AnswerInterface;

public class Answer<K, V> implements AnswerInterface<K, V> {
    private K k;
    private V v;

    public Answer() {
    }

    @JsonCreator
    public Answer(@JsonProperty("key") K k, @JsonProperty("value") V v) {
        this.k = k;
        this.v = v;
    }

    public K getKey() {
        return k;
    }

    public V getValue() {
        return v;
    }
}
