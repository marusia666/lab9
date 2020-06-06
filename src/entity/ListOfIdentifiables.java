package entity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;

public class ListOfIdentifiables<T extends Identifiable & Serializable> implements Serializable {
    private static final long serialVersionUID = -4621472982618921772L;
    protected HashSet<T> items = new HashSet<T>();
    private transient Integer nextId;
    public ListOfIdentifiables() {
// Исходное значение следующего незанятого идентификатора - 1
        nextId = 1;
    }
    // Читает из объектного потока набор элементов и помещает их в хэшмассив.
    // При этом происходит вычисление максимального идентификационного номера.
    // Данный метод автоматически вызывается в ходе процедуры десериализации.
    @SuppressWarnings("unchecked")
    private void readObject(final ObjectInputStream in) throws IOException,
            ClassNotFoundException {
        // Читаем сохраненное в файле хэш-множество
        items = (HashSet<T>)in.readObject();
        // Ищем максимальное значение идентификатора пользователя
        nextId = 0;
        for(T item : items) {
            final Integer itemId = item.getId();
            if(itemId > nextId)
                nextId = itemId;
        }
        // Доступное значение идентификатора будет на 1 больше
        // максимального имеющегося
        nextId++;
    }
    // Записывает в объектный поток содержимое хэш-массива.
    // Данный метод автоматически вызывается в ходе процедуры десериализации.
    private void writeObject(final ObjectOutputStream out) throws IOException {
        // Записываем весь хэш-массив в объектный поток
        out.writeObject(items);
    }
    // Возвращает следующее значение идентификатора.
    protected int getNextId() {
        return nextId++;
    }
}
