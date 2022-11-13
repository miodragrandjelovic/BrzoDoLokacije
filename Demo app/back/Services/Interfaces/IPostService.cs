namespace PyxisKapriBack.Services.Interfaces
{
    public interface IPostService
    {
        List<Post> GetUserPosts(int UserID);
        List<Post> GetPostsForLocation(int LocationID);
        void AddPost(Post post);
        void DeletePost(Post post);
        Post GetPost(int PostID);
    }
}
