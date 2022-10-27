namespace PyxisKapriBack.Models.Interfaces
{
    public interface IEncryptionManager
    {
        public void EncryptPassword(string password,out byte[] passwordHash,out byte[] passwordKey);
        public bool DecryptPassword(string password, byte[] passwordHash, byte[] passwordKey);
    }
}
