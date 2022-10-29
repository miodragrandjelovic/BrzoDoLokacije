using PyxisKapriBack.Models;

namespace PyxisKapriBack.DTOComponents
{
    public class UserDTO
    {
        public string Token { get; set; }
        public string Username { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string Email { get; set; }

        // public string Country { get; set }

        // DODATI OSTATAK INFORMACIJA KORISNIKA KOJE CE SE CUVATI U LOKALNOM SKLADISTU KORISNIKA I KORISTITI ZA PRIKAZ INFORMACIJA KORISNIKA BE STALNOG POZIVA BACK-A //


        // IZMENITI KADA SE DODAJU NOVE PROMENLJIVE U User KLASI //
        public UserDTO(User user)
        {
            Username = user.Username;
            FirstName = user.FirstName;
            LastName = user.LastName;   
            Email = user.Email;
            
        }
    }
}
