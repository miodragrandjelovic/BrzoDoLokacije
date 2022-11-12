using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Services.Interfaces;
using PyxisKapriBack.UI.Interfaces;

namespace PyxisKapriBack.UI
{
    public class FollowUI : IFollowUI
    {
        private IFollowService _iFollowService;
        private IUserService _iUserService;
        public FollowUI(IFollowService iFollowService, IUserService iUserService)
        {
            _iFollowService = iFollowService;
            _iUserService   = iUserService;
        }

        public List<UserDTO> GetFollowers()
        {
            var followers = _iFollowService.GetFollowers(_iUserService.GetLoggedUser());

            if (followers == null)
                return null; 

            var followersDTO  = new List<UserDTO>();

            foreach(var follower in followers)
            {
                followersDTO.Add(new UserDTO
                {
                    ProfileImage = follower.ProfileImage == null ? string.Empty : Convert.ToBase64String(follower.ProfileImage),
                    Username = follower.Username,
                    FirstName = follower.FirstName,
                    LastName = follower.LastName,
                    Email = follower.Email
                }); 
            }
            return followersDTO;
        }

        public List<UserDTO> GetFollowing()
        {
            throw new NotImplementedException();
        }
    }
}
