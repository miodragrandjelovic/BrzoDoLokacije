namespace PyxisKapriBack.DTOComponents
{
    public class UserShortDTO
    {
        public string ProfileImage { get; set; } = string.Empty;
        public string Username { get; set; } = string.Empty;
        public string FirstName { get; set; } = string.Empty;
        public string LastName { get; set; } = string.Empty;
        public bool IsFollowed { get; set; }
    }
}
