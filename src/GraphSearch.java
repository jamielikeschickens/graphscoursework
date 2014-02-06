import java.io.IOException;


public class GraphSearch {

	public static void main(String[] args) {
		String fileName = args[1];
		
		if (args[0].equals("-p1")) {
			Reader reader = new Reader();
			try {
				reader.read(fileName);
			} catch (IOException e) {
				System.err.println("Invalid file name");
				System.exit(1);
			}
			GraphSerializer serializer = new GraphSerializer(reader.graph());
			System.out.print(serializer.serialize());
		}
	}

}
