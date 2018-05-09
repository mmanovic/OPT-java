package hr.fer.zemris.optjava.rng;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RNG {
	private static IRNGProvider rngProvider;

	static {
		// Stvorite primjerak razreda Properties;
		// Nad Classloaderom razreda RNG tra�ite InputStream prema resursu
		// rng-config.properties
		// recite stvorenom objektu razreda Properties da se u�ita podatcima iz
		// tog streama.
		// Dohvatite ime razreda pridru�eno klju�u "rng-provider"; zatra�ite
		// Classloader razreda
		// RNG da u�ita razred takvog imena i nad dobivenim razredom pozovite
		// metodu newInstance()
		// kako biste dobili jedan primjerak tog razreda; castajte ga u
		// IRNGProvider i zapamtite.

		try {
			Properties properties = new Properties();
			InputStream is = RNG.class.getClassLoader().getResourceAsStream("rng-config.properties");
			properties.load(is);
			String className = properties.getProperty("rng-provider");
			rngProvider = (IRNGProvider) RNG.class.getClassLoader().loadClass(className).newInstance();
		} catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static IRNG getRNG() {
		return rngProvider.getRNG();
	}
}
