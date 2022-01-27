public record Course(String code, String name, int credits)
{
    public String getCode()
    {
        return this.code;
    }

    public String getName()
    {
        return this.name;
    }

    public int getCredits()
    {
        return this.credits;
    }
}
