package jrcengine.Interface;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IFace_FileIO {
	public InputStream readAsset(String fileName) throws IOException;

	public InputStream readFile(String fileName) throws IOException;

	public OutputStream writeFile(String fileName) throws IOException;

}
