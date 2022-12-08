using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.UI.Interfaces
{
    public interface IFollowUI
    {
        public List<UserShortDTO>? GetFollowers(string username = ""); 
        public List<UserShortDTO>? GetFollowing(string username = "");
        Response AddFollow(string followingUsername);
        Response DeleteFollow(string followingUsername);
        Response IsFollowed(string followingUsername);
        public List<UserShortDTO> SearchFollowers(string search);
        public List<UserShortDTO> SearchFollowing(string search); 
    }
}
