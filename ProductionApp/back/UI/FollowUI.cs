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

        public List<UserShortDTO>? GetFollowers(string username = "")
        {
            if(String.IsNullOrEmpty(username))
                return createListUserShortDTO(_iFollowService.GetFollowers(_iUserService.GetLoggedUser()));
            return createListUserShortDTO(_iFollowService.GetFollowers(username));
        }

        public List<UserShortDTO>? GetFollowing(string username = "")
        {
            if (String.IsNullOrEmpty(username))
                return createListUserShortDTO(_iFollowService.GetFollowing(_iUserService.GetLoggedUser()));
            return createListUserShortDTO(_iFollowService.GetFollowing(username));
        }

        public Response IsFollowed(string followingUsername)
        {
            return _iFollowService.IsFollowed(_iUserService.GetLoggedUser(), followingUsername); 
        }

        public List<UserShortDTO> SearchFollowers(UserSearchDTO userSearchDTO)
        {
            return createListUserShortDTO(_iFollowService.SearchFollowers(userSearchDTO.Username, userSearchDTO.Search));
        }

        public List<UserShortDTO> SearchFollowing(UserSearchDTO userSearchDTO)
        {
            return createListUserShortDTO(_iFollowService.SearchFollowing(userSearchDTO.Username, userSearchDTO.Search)); 
        }

        public List<UserShortDTO> createListUserShortDTO(List<User> list, bool followers = true)
        {
            var listUserShortDTO = new List<UserShortDTO>();
            List<String> listFollow; 
            listFollow = _iFollowService.GetFollowing(_iUserService.GetLoggedUser()).Select(user => user.Username).ToList();

            foreach (var item in list)
            {
                listUserShortDTO.Add(new UserShortDTO
                {
                    FirstName = item.FirstName,
                    LastName = item.LastName,
                    Username = item.Username,
                    ProfileImage = Path.Combine(item.FolderPath, item.FileName),
                    IsFollowed = listFollow.Contains(item.Username)
                });
            }
            return listUserShortDTO;
        }
    }
}
