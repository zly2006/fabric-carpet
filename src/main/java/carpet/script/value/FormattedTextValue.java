package carpet.script.value;

import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.text.BaseText;
import net.minecraft.text.Text;

public class FormattedTextValue extends StringValue
{
    Text text;
    public FormattedTextValue(Text text)
    {
        super(null);
        this.text = text;
    }

    @Override
    public String getString() {
        return text.getString();
    }

    @Override
    public boolean getBoolean() {
          return text.getSiblings().size() > 0;
    }

    @Override
    public Value clone()
    {
        return new FormattedTextValue(text.shallowCopy());
    }

    @Override
    public String getTypeString()
    {
        return "text";
    }

    public Text getText()
    {
        return text;
    }

    @Override
    public Tag toTag(boolean force)
    {
        if (!force) throw new NBTSerializableValue.IncompatibleTypeException(this);
        return StringTag.of(Text.Serializer.toJson(text));
    }

    @Override
    public Value add(Value o) {
        if (o instanceof FormattedTextValue)
        {
            Text mergedText = ((BaseText) text).shallowCopy().append(((FormattedTextValue) o).getText());
            return new FormattedTextValue(mergedText);
        }
        else
        {
            Text mergedText = ((BaseText) text).shallowCopy().append(o.getString());
            return new FormattedTextValue(mergedText);
        }
    }

    public String serialize()
    {
        return Text.Serializer.toJson(text);
    }

    public static FormattedTextValue deserialize(String serialized)
    {
        return new FormattedTextValue(Text.Serializer.fromJson(serialized));
    }

}
