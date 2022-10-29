using PyxisKapriBack.DAL.Interfaces;

namespace PyxisKapriBack.DAL
{
    public class PostDAL : IPostDAL
    {
        private readonly Database _context;
        private readonly IUserDAL _iUserDAL; 
        public PostDAL(Database context, IUserDAL iUserDAL)
        {
            _context = context;
            _iUserDAL = iUserDAL;
        }
        public void AddPost(Post post)
        {
            _context.Posts.Add(post);
            _context.SaveChanges(); 
        }

        public void DeletePost(int PostID)
        {
            Post post = _context.Posts.Find(PostID);
            _context.Posts.Remove(post);
            _context.SaveChanges(); 
        }

        public Post GetPost(int PostID)
        {
            return _context.Posts.Where(post => post.Id == PostID).FirstOrDefault(); 
        }

        public void UpdatePost(Post post)
        {
            _context.Posts.Update(post);
            _context.SaveChanges();
        }

        public List<Post> GetPostsForLocation(int LocationID)
        {
            return _context.Posts.Where(post => post.LocationId == LocationID).ToList(); 
        }

        public List<Post> GetUserPosts(string username)
        {
            User user = _iUserDAL.GetUser(username); 
            return _context.Posts.Where(post => post.UserId == user.Id).ToList(); 
        }
    }
}
