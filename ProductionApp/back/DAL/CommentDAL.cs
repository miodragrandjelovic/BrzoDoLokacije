﻿using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.DAL
{
    public class CommentDAL : ICommentDAL
    {
        private Database _context; 

        public CommentDAL(Database context)
        {
            _context = context;
        }

        public int AddComment(Comment Comment)
        {
            if (Comment == null)
                return -1; 
            _context.Comments.Add(Comment);
            _context.SaveChanges();
            
            return Comment.Id;
        }

        public bool DeleteComment(int CommentID)
        {
            Comment comment = GetComment(CommentID);
            if (comment == null)
                throw new Exception(Constants.Constants.resNoFoundComment); 
            _context.Comments.Remove(comment);
            _context.SaveChanges();
            return true; 
        }

        public Comment GetComment(int CommentID)
        {
            return _context.Comments.Where(comment => comment.Id == CommentID).Include(comment => comment.User)
                                                                              .Include(comment => comment.Post)
                                                                              .Include(comment => comment.Replies)
                                                                              .FirstOrDefault(); 
        }

        public List<Comment> GetCommentsPost(int PostID)
        {
            return _context.Comments.Where(comment => comment.PostId == PostID)
                                    .Include(comment => comment.User)
                                    .OrderBy(comment => comment.DateCreated)
                                    .ToList(); 
        }

        public List<Comment> GetReplies(int CommentID)
        {
            return _context.Comments.Where(comment => comment.CommentParentId == CommentID)
                                    .Include(comment => comment.User)
                                    .Include(comment => comment.Post)
                                    .OrderBy(comment => comment.DateCreated)
                                    .ToList(); 
        }

        public bool UpdateComment(Comment Comment)
        {
            if (Comment == null)
                return false;
            _context.Update(Comment);
            _context.SaveChanges();
            return true; 
        }
    }
}
