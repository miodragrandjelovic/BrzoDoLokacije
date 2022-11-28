using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.UI.Interfaces
{
    public interface IPostUI
    {
        List<PostDTO> GetUserPosts(string username);
        List<PostDTO> GetPostsForLocation(int LocationID);
        void AddPost(NewPostDTO post);
        Response DeletePost(int postID);
        AdditionalPostData GetPost(int PostID);
        Response SetLikeOnPost(int postID);
        Response DeleteUserPost(int postID);
        List<PostDTO> GetAllPosts();
        Response RemoveLikeFromPost(int postID);
        List<PostDTO> GetFollowingPosts(SortType sortType = SortType.DATE);
        List<PostDTO> GetRecommendedPosts(SortType sortType = SortType.DATE);
    }
}
