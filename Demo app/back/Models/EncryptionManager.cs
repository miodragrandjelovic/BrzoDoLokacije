using PyxisKapriBack.Models.Interfaces;
using System.Security.Cryptography;

namespace PyxisKapriBack.Models
{
    public class EncryptionManager : IEncryptionManager
    {
        public void EncryptPassword(string password, out byte[] passwordHash, out byte[] passwordKey)
        {
            using (var hmac = new HMACSHA512())
            {
                passwordKey = hmac.Key;

                passwordHash = hmac.ComputeHash(System.Text.Encoding.UTF8.GetBytes(password));

            }
        }

        public bool DecryptPassword(string userPassword, byte[] password, byte[] passwordKey)
        {
            bool answer = false;
            using (var hmac = new HMACSHA512(passwordKey))
            {
                var passwordHash = hmac.ComputeHash(System.Text.Encoding.UTF8.GetBytes(userPassword));

                answer = passwordHash.SequenceEqual(password);
            }


            return answer;
        }

        
    }
}
