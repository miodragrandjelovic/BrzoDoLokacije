using PyxisKapriBack.DAL.Interfaces;

namespace PyxisKapriBack.DAL
{
    public class PostDAL : IPostDAL
    {
        private readonly Database _context;
        public PostDAL(Database context)
        {
            _context = context;
        }

        public List<Post> GetPostsForLocation(int LocationID)
        {
            return _context.Locations.Where(location => location.Id == LocationID).First().Posts.ToList();
        }

        public List<Post> GetUserPosts(int UserID)
        {
            return _context.Users.Where(user => user.Id == UserID).First().Posts.ToList();
        }

        public void AddPost(Post post)
        {
            _context.Posts.Add(post);
            _context.SaveChanges();
        }

        public void UpdatePost(Post post)
        {
            _context.Update(post);
            _context.SaveChanges(); 
        }

        public void DeletePost(Post post)
        {
            _context.Remove(post);
            _context.SaveChanges(); 
        }

        Post IPostDAL.GetPost(int PostID)
        {
            return _context.Posts.Where(post => post.Id == PostID).First(); 
        }
    }
}
