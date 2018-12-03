import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class EscreverProgramaObj {

	public static void EscreverProgramaObjeto(String text) {

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(
					"/home/carlsen/Desktop/Faculdade/Compiladores/Projetos/Virtual-Machine/programaObj.txt"));
			writer.write(text);

		} catch (IOException e) {
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
			}
		}

	}
	
	public static void EscreverCodigoFonte(String text) {

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(
					"/home/carlsen/Desktop/Faculdade/Compiladores/Projetos/CSD/codigoFonte.txt"));
			writer.write(text);

		} catch (IOException e) {
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
			}
		}

	}

}
