import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Namer {
    private final Random random;

    Namer(){
        random = new Random();
        String[] names = {"Juan", "María", "Carlos", "Ana", "Luis", "Laura", "Pedro", "Isabel"};
        namesList = new ArrayList<>(Arrays.asList(names));
    }

   public ArrayList<String> namesList;

    public String getRandomName(){
        var index = random.nextInt(namesList.size());
        var name = namesList.get(index);
        namesList.remove(index);
        return name;
    }
}
