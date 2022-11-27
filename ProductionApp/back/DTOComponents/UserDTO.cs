using PyxisKapriBack.Models;

namespace PyxisKapriBack.DTOComponents
{
    public class UserDTO
    {
        public string Username { get; set; } = string.Empty;
        public string Password { get; set; } = string.Empty;
        public string FirstName { get; set; } = string.Empty;
        public string LastName { get; set; } = string.Empty;
        public string Email { get; set; } = string.Empty;

        public string ProfileImagePath { get; set; } = string.Empty;




    }
}
