using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;
namespace PyxisKapriBack.Services.Interfaces
{
    public interface IPostService
    {
        Response GetUserPosts(string username, SortType sortType = SortType.DATE); 
        List<Post> GetPostsForLocation(int LocationID);
        void AddPost(NewPostDTO post);
        Response DeletePost(int postID);
        Post GetPost(int PostID);
        Response SetLikeOnPost(int postID);
        Response SetLikeOnPost(LikeDTO likeDTO);
        Response DeleteUserPost(int postID);
        List<Post> GetAllPosts(SortType sortType = SortType.DATE);
        List<Post> GetFollowingPosts(string username, SortType sortType = SortType.DATE);
        Response GetRecommendedPosts(string username, SortType sortType = SortType.DATE);
        Response GetPostsBySearch(SearchDTO searchDTO);
        List<Post> GetAllAroundPosts(double latitude, double longitude, double distance = Constants.Constants.DISTANCE, bool friendsOnly = false);
        double GetAverageGrade(int postId);
        int GetGrade(int postId, string username); 
    }
}
