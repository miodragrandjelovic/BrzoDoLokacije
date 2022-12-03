using PyxisKapriBack.Models;
namespace PyxisKapriBack.DAL.Interfaces
{
    public interface IPostDAL
    {
        void AddPost(Post post);
        bool DeletePost(int PostID);
        void UpdatePost(Post post);
        Post GetPost(int PostID);
        List<Post> GetUserPosts(String username);
        List<Post> GetPostsForLocation(int LocationID);
        List<Post> GetPosts(String username, SortType sortType = SortType.DATE);
        List<Post> GetFollowingPosts(string username, SortType sortType = SortType.DATE);
        List<Post> GetRecommendedPosts(string username, SortType sortType = SortType.DATE);
        List<Post> GetPostsBySearch(String search, SortType sortType = SortType.DATE); 
    }
}
