﻿using PyxisKapriBack.DAL.Interfaces;

namespace PyxisKapriBack.DAL
{
    public class UserDAL : IUserDAL
    {
        private readonly Database _context;
        public UserDAL(Database context)
        {
            _context = context; 
        }
        public void AddNewUser(User user)
        {
            _context.Users.Add(user);
            _context.SaveChanges();
        }

        public User? GetUser(string username)
        {
            return _context.Users.Where(x => x.Username.Equals(username)).FirstOrDefault();
        }

        public async Task<bool> UserAlreadyExists(string username)
        {
            return await _context.Users.AnyAsync(user => user.Username.Equals(username));
        }
    }
}