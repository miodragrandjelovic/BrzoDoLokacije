using PyxisKapriBack.DTOComponents;

namespace PyxisKapriBack.UI.Interfaces
{
    public interface IFollowUI
    {
        public List<UserDTO> GetFollowers(); 
        public List<UserDTO> GetFollowing();
    }
}
