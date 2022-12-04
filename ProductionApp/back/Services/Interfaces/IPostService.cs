using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;
namespace PyxisKapriBack.Services.Interfaces
{
    public interface IPostService
    {
        Response GetUserPosts(string username); 
        List<Post> GetPostsForLocation(int LocationID);
        void AddPost(NewPostDTO post);
        Response DeletePost(int postID);
        Post GetPost(int PostID);
        Response SetLikeOnPost(int postID);
        Response DeleteUserPost(int postID);
        List<Post> GetAllPosts(SortType sortType = SortType.DATE);
        List<Post> GetFollowingPosts(string username, SortType sortType = SortType.DATE);
        Response GetRecommendedPosts(string username, SortType sortType = SortType.DATE);
        Response GetPostsBySearch(String search, SortType sortType = SortType.DATE, int countOfResult = Constants.Constants.TAKE_ELEMENT);
    }
}
