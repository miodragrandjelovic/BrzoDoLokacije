namespace PyxisKapriBack.Services.Interfaces
{
    public interface IPostService
    {
        List<Post> GetUserPosts(string username);
        List<Post> GetPostsForLocation(int LocationID);
        void AddPost(Post post);
        void DeletePost(int postID);
        Post GetPost(string username);
    }
}
