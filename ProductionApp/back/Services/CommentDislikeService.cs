using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Services.Interfaces;
using PyxisKapriBack.Models;
namespace PyxisKapriBack.Services
{
    public class CommentDislikeService : ICommentDislikeService
    {
        private readonly ICommentDislikeDAL _iCommentDislikeDAL;
        
        public CommentDislikeService(ICommentDislikeDAL commentDislikeDAL)
        {
            _iCommentDislikeDAL = commentDislikeDAL;
        }
    }
}
