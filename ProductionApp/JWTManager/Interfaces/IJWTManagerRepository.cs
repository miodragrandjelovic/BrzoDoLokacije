namespace PyxisKapriBack.JWTManager.Interfaces
{
    public interface IJWTManagerRepository
    {
        string GenerateToken(User user);

        string? ReadToken(string token);
    }
}
