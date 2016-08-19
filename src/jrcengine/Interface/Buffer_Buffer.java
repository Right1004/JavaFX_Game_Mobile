package jrcengine.Interface;

import java.util.ArrayList;
import java.util.List;

public class Buffer_Buffer<T> {
	public interface BufferObjectFactory<T> {
		public T createObject();
	}

	private final List<T> freeObjects;
	private final BufferObjectFactory<T> factory;
	private final int maxSize;

	public Buffer_Buffer(BufferObjectFactory<T> factory, int maxSize) {
		this.factory = factory;
		this.maxSize = maxSize;
		this.freeObjects = new ArrayList<T>(maxSize);
	}

	public T newObject() {
		T object = null;

		if (freeObjects.size() == 0)
			object = factory.createObject();
		else
			object = freeObjects.remove(freeObjects.size() - 1);

		return object;
	}

	public void free(T object) {
		if (freeObjects.size() < maxSize)
			freeObjects.add(object);
	}
}
