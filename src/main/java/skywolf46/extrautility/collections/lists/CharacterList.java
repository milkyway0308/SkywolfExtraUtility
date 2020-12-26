package skywolf46.extrautility.collections.lists;

public class CharacterList extends AppendableList<Character> {

    @Override
    public CharacterList append(Character character) {
        super.append(character);
        return this;
    }
}
