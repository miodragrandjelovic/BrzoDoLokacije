using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.LocationManager.Interfaces;
using PyxisKapriBack.Models;
using System.Device.Location;

namespace PyxisKapriBack.DAL
{
    public class PostDAL : IPostDAL
    {
        private readonly Database _context;
        private readonly IUserDAL _iUserDAL; 
        private readonly ILocationDAL locationDAL;
        private readonly ILocationManager locationManager;
        private readonly IFollowDAL followDAL; 
        public PostDAL(Database context, IUserDAL iUserDAL, ILocationDAL ilocationDAL, ILocationManager ilocationManager, IFollowDAL followDAL)
        {
            _context = context;
            _iUserDAL = iUserDAL;
            locationDAL = ilocationDAL;
            locationManager = ilocationManager;
            this.followDAL = followDAL;
        }
        public void AddPost(Post post)
        {
            _context.Posts.Add(post);
            _context.SaveChanges(); 
        }

        public bool DeletePost(int PostID)
        {
            Post post = GetPost(PostID);

            if (post == null)
                return false; 

            _context.Posts.Remove(post);
            _context.SaveChanges();
            return true; 
        }

        public Post GetPost(int PostID)
        {
            return _context.Posts.Where(post => post.Id == PostID).Include(post => post.User)
                                                                  .Include(post => post.Dislikes)
                                                                  .Include(post => post.Likes)
                                                                  .Include(post => post.Comments)
                                                                  .Include(post => post.Images)
                                                                  .Include(post => post.Location)
                                                                  .OrderByDescending(post => post.CreatedDate)
                                                                  .FirstOrDefault(); 
        }

        public void UpdatePost(Post post)
        {
            _context.Posts.Update(post);
            _context.SaveChanges();
        }

        public List<Post> GetPostsForLocation(int LocationID)
        {
            return _context.Posts.Where(post => post.LocationId == LocationID).Include(post => post.User)
                                                                              .Include(post => post.Dislikes)
                                                                              .Include(post => post.Likes)
                                                                              .Include(post => post.Comments)
                                                                              .Include(post => post.Images)
                                                                              .Include(post => post.Location)
                                                                              .OrderByDescending(post => post.CreatedDate)
                                                                              .ToList(); 
        }

        public List<Post> GetUserPosts(string username, SortType sortType = SortType.DATE)
        {
            User user = _iUserDAL.GetUser(username);
            if (user == null)
                throw new Exception(Constants.Constants.resNoFoundUser);

            var posts = _context.Posts.Where(post => post.UserId == user.Id).Include(post => post.User)
                                                                       .Include(post => post.Dislikes)
                                                                       .Include(post => post.Likes)
                                                                       .Include(post => post.Comments)
                                                                       .Include(post => post.Images)
                                                                       .Include(post => post.Location);
            return SortListByCriteria(posts, sortType);
        }

        public List<Post> GetPosts(string username, SortType sortType = SortType.DATE)
        {
            User user = _iUserDAL.GetUser(username);
            if (user == null)
                throw new Exception(Constants.Constants.resNoFoundUser);

            var posts = _context.Posts.Where(post => post.UserId != user.Id).Include(post => post.User)
                                                                       .Include(post => post.Dislikes)
                                                                       .Include(post => post.Likes)
                                                                       .Include(post => post.Comments)
                                                                       .Include(post => post.Images)
                                                                       .Include(post => post.Location);
            return SortListByCriteria(posts, sortType); 
        }

        public List<Post> GetFollowingPosts(string username, SortType sortType = SortType.DATE)
        {
            var _user = _iUserDAL.GetUser(username);
            if (_user == null)
                throw new Exception(Constants.Constants.resNoFoundUser);
            List<int> users = _context.Follow.Where(follow => follow.FollowerId == _user.Id)
                                                          .Select(follow => follow.Following)
                                                          .Select(user => user.Id)
                                                          .ToList();

            IQueryable<Post> posts = _context.Posts.Where(post => users.Contains(post.UserId))
                                                .Include(post => post.User)
                                                .Include(post => post.Dislikes)
                                                .Include(post => post.Likes)
                                                .Include(post => post.Comments)
                                                .Include(post => post.Images)
                                                .Include(post => post.Location); 

            return SortListByCriteria(posts, sortType); 
        }

        public List<Post> GetRecommendedPosts(string username, SortType sortType = SortType.DATE)
        { 
            var _user = _iUserDAL.GetUser(username);
            if (_user == null)
                throw new Exception(Constants.Constants.resNoFoundUser);
            List<int> users = _context.Follow.Where(follow => follow.FollowerId == _user.Id)
                                                          .Select(follow => follow.Following)
                                                          .Select(user => user.Id)
                                                          .ToList();
            users.Add(_user.Id);

            IQueryable<Post> posts = _context.Posts.Where(post => !users.Contains(post.UserId))
                                             .Include(post => post.User)
                                             .Include(post => post.Dislikes)
                                             .Include(post => post.Likes)
                                             .Include(post => post.Comments)
                                             .Include(post => post.Images)
                                             .Include(post => post.Location); 

            return SortListByCriteria(posts, sortType);
        }

        List<Post> SortListByCriteria(IQueryable<Post> orderedQueryable, SortType sortType = SortType.DATE)
        {
            if (sortType == SortType.DATE)
                orderedQueryable = orderedQueryable.OrderByDescending(post => post.CreatedDate);
            else if (sortType == SortType.COUNT_COMMENTS)
                orderedQueryable = orderedQueryable.OrderByDescending(post => post.Comments.Count);
            else if (sortType == SortType.COUNT_LIKES)
            {
                orderedQueryable = orderedQueryable.OrderByDescending(post => post.Likes.Sum(post => post.Grade) / post.Likes.Count()); 
            }

            return orderedQueryable.ToList(); 
        }

        public List<Post> GetPostsBySearch(String username, String search, SearchType searchType, SortType sortType = SortType.DATE, bool friendsOnly = false)
        {
            IQueryable<Post> posts = _context.Posts;

            if (friendsOnly)
            {
                var users = followDAL.GetFollowing(username).Select(user => user.Id);
                posts = _context.Posts.Where(post => users.Contains(post.UserId));
            }
            
            if (!String.IsNullOrEmpty(search))
            {
                if(searchType == SearchType.LOCATION)
                    posts = posts.Where(post => post.FullLocation.ToLower().Contains(search.ToLower()));
                else if(searchType == SearchType.TAGS)
                {
                    List<String> tags = search.Split(", ").ToList();
                    /*posts = posts.Where(post => !String.IsNullOrEmpty(post.Tags))
                                 .Where(post => tags.Any(post.Tags.Contains())); */
                    posts = posts.Where(post => !String.IsNullOrEmpty(post.Tags));
                    var filteredPosts = posts.ToList()
                                             .Where(post => tags.Any(tag => post.Tags.ToLower().Contains(tag.ToLower())))
                                             .AsQueryable()
                                             .Select(post => post.Id)
                                             .ToList();
                    posts = posts.Where(post => filteredPosts.Contains(post.Id)); 
                }
            }

            posts = posts.Include(post => post.User)
                         .Include(post => post.Dislikes)
                         .Include(post => post.Likes)
                         .Include(post => post.Comments);
            return SortListByCriteria(posts, sortType);
        }

        public List<Post> GetPostsByCoordinates(string username, double latitude, double lognitude, double distance = Constants.Constants.DISTANCE, bool friendsOnly = false)
        {
            GeoCoordinate coordinate = new GeoCoordinate(latitude, lognitude);
            List<Post> posts = new List<Post>();
            User user = _iUserDAL.GetUser(username);
            
            if (friendsOnly == false)
                posts = _context.Posts.ToList();
            else
            {
                var followings = followDAL.GetFollowing(username);
                foreach (var following in followings)
                    if (following.Posts != null)
                    {
                        foreach (var post in following.Posts)
                            posts.Add(post);
                    }
            }
            return locationManager.GetAllAroundPosts(coordinate, posts, distance); 
        }

        public double GetAverageGrade(int postId)
        {
            var post = _context.Posts.Where(post => post.Id.Equals(postId)).FirstOrDefault();
            if (post == null)
                return 0;
            var sum = _context.Likes.Where(like => like.PostId.Equals(postId)).Sum(like => like.Grade); 
            var count = _context.Likes.Where(like => like.PostId.Equals(postId)).Count();

            if (sum == 0)
                return 0;
            if (count == 0)
                return 0;
            return (sum*1.0) / count; 
        }
    }
}
