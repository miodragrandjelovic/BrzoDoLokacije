using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;
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

        public Response AddFollow(string followingUsername)
        {
            return _iFollowService.AddFollow(_iUserService.GetLoggedUser(), followingUsername); 
        }

        public Response DeleteFollow(string followingUsername)
        {
            return _iFollowService.DeleteFollow(_iUserService.GetLoggedUser(), followingUsername);
        }

        public List<FollowDTO>? GetFollowers()
        {
            var followers = _iFollowService.GetFollowers(_iUserService.GetLoggedUser());

            var followersDTO = new List<FollowDTO>();

            foreach (var follow in followers)
            {
                followersDTO.Add(new FollowDTO
                {
                    ProfileImage = follow.ProfileImage == null ? string.Empty : Convert.ToBase64String(follow.ProfileImage),
                    Username = follow.Username,
                    FirstName = follow.FirstName,
                    LastName = follow.LastName
                });
            }
            return followersDTO;
        }

        public List<FollowDTO>? GetFollowing()
        {
            var followings = _iFollowService.GetFollowing(_iUserService.GetLoggedUser());

            var followingDTO = new List<FollowDTO>();

            foreach (var follow in followings)
            {
                followingDTO.Add(new FollowDTO
                {
                    ProfileImage = follow.ProfileImage == null ? string.Empty : Convert.ToBase64String(follow.ProfileImage),
                    Username = follow.Username,
                    FirstName = follow.FirstName,
                    LastName = follow.LastName
                });
            }
            return followingDTO;
        }

        public Response IsFollowed(string followingUsername)
        {
            return _iFollowService.IsFollowed(_iUserService.GetLoggedUser(), followingUsername); 
        }
    }
}
