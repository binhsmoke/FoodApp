using System;
using System.Collections.Generic;

#nullable disable

namespace MasJoheun.Models
{
    public partial class Client
    {
        public Client()
        {
            Favourites = new HashSet<Favourite>();
            Receipts = new HashSet<Receipt>();
        }

        public string Phone { get; set; }
        public string Password { get; set; }
        public string Name { get; set; }
        public string Address { get; set; }
        public int OrderTime { get; set; }

        public virtual ICollection<Favourite> Favourites { get; set; }
        public virtual ICollection<Receipt> Receipts { get; set; }
    }
}
