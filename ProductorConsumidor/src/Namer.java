import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Namer {
    private final Random random;
    public ArrayList<String> namesList;
    private final ArrayList<String> namesListCopy;

    Namer(){
        random = new Random();
        String[] names = {"Juan", "María", "Carlos", "Ana", "Luis", "Laura", "Pedro", "Isabel"};
        namesList = new ArrayList<>(Arrays.asList(names));
        namesListCopy = new ArrayList<>(Arrays.asList(names));
    }


    public String getRandomName(){
        var index = random.nextInt(namesListCopy.size());
        var name = namesListCopy.get(index);
        namesListCopy.remove(index);
        return name;
    }
}
