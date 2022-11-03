import java.util.Scanner;
import java.io.*;
import java.util.*;

public class CoordinateMaker {

	public void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
	    ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("Files/coords.txt").getFile());
        Scanner sc = new Scanner(file);
		//Scanner sc = new Scanner(new File("coords.txt"));
		String str = "";
		while (sc.hasNextLine())
			str += sc.nextLine() + "  ";
		System.out.println(str);
		String[] st = str.split("  ");
		ArrayList x = new ArrayList();
		ArrayList y = new ArrayList();
		for (int i = 0; i < st.length; i++) {
			x.add(st[i].substring(0, st[i].indexOf(",")));
			y.add(st[i].substring(st[i].indexOf(" ")+1, st[i].length()));
		}
		System.out.println("x-"+x);
		System.out.println("y-"+y);
	}
}
