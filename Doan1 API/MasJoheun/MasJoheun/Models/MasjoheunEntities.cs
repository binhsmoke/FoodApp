using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

#nullable disable

namespace MasJoheun.Models
{
    public partial class MasjoheunEntities : DbContext
    {
      
        public MasjoheunEntities(DbContextOptions<MasjoheunEntities> options)
            : base(options)
        {
        }

        public virtual DbSet<City> Cities { get; set; }
        public virtual DbSet<Client> Clients { get; set; }
        public virtual DbSet<Combo> Combos { get; set; }
        public virtual DbSet<ComboDetail> ComboDetails { get; set; }
        public virtual DbSet<Event> Events { get; set; }
        public virtual DbSet<Favourite> Favourites { get; set; }
        public virtual DbSet<Food> Foods { get; set; }
        public virtual DbSet<FoodType> FoodTypes { get; set; }
        public virtual DbSet<Receipt> Receipts { get; set; }
        public virtual DbSet<ReceiptDetail> ReceiptDetails { get; set; }
        public virtual DbSet<Restaurant> Restaurants { get; set; }
        public virtual DbSet<Vouncher> Vounchers { get; set; }


        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.HasDefaultSchema("doan1")
                .HasAnnotation("Relational:Collation", "SQL_Latin1_General_CP1_CI_AS");

            modelBuilder.Entity<City>(entity =>
            {
                entity.ToTable("City");

                entity.Property(e => e.Id).HasColumnName("ID");

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasMaxLength(255);
            });

            modelBuilder.Entity<Client>(entity =>
            {
                entity.HasKey(e => e.Phone)
                    .HasName("PK__Client__5C7E359F1E976187");

                entity.ToTable("Client");

                entity.Property(e => e.Phone).HasMaxLength(20);

                entity.Property(e => e.Address)
                    .IsRequired()
                    .HasMaxLength(255);

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasMaxLength(255);

                entity.Property(e => e.Password)
                    .IsRequired()
                    .HasMaxLength(255)
                    .IsUnicode(false)
                    .IsFixedLength(true);
            });

            modelBuilder.Entity<Combo>(entity =>
            {
                entity.ToTable("Combo");

                entity.Property(e => e.Id).HasColumnName("ID");

                entity.Property(e => e.Image)
                    .IsRequired()
                    .HasMaxLength(255);

                entity.Property(e => e.IsAvailable)
                    .IsRequired()
                    .HasMaxLength(3);

                entity.Property(e => e.NameCombo)
                    .IsRequired()
                    .HasMaxLength(255);
            });

            modelBuilder.Entity<ComboDetail>(entity =>
            {
                entity.HasKey(e => new { e.IdFood, e.IdCombo })
                    .HasName("PK__ComboDet__2FBEA324C126F268");

                entity.ToTable("ComboDetail");

                entity.Property(e => e.IdFood).HasColumnName("ID_Food");

                entity.Property(e => e.IdCombo).HasColumnName("ID_Combo");

                entity.HasOne(d => d.IdComboNavigation)
                    .WithMany(p => p.ComboDetails)
                    .HasForeignKey(d => d.IdCombo)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK__ComboDeta__ID_Co__76619304");

                entity.HasOne(d => d.IdFoodNavigation)
                    .WithMany(p => p.ComboDetails)
                    .HasForeignKey(d => d.IdFood)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK__ComboDeta__ID_Fo__756D6ECB");
            });

            modelBuilder.Entity<Event>(entity =>
            {
                entity.ToTable("Event");

                entity.Property(e => e.Id).HasColumnName("ID");

                entity.Property(e => e.Description)
                    .IsRequired()
                    .HasMaxLength(4000);

                entity.Property(e => e.IdRestaurant).HasColumnName("ID_Restaurant");

                entity.Property(e => e.Image)
                    .IsRequired()
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasMaxLength(255);

                entity.HasOne(d => d.IdRestaurantNavigation)
                    .WithMany(p => p.Events)
                    .HasForeignKey(d => d.IdRestaurant)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK__Event__ID_Restau__01D345B0");
            });

            modelBuilder.Entity<Favourite>(entity =>
            {
                entity.HasKey(e => new { e.Id, e.Phone })
                    .HasName("PK__Favourit__D7D30F7EA5479C45");

                entity.ToTable("Favourite");

                entity.Property(e => e.Id).HasColumnName("ID");

                entity.Property(e => e.Phone).HasMaxLength(20);

                entity.Property(e => e.IsFavourited)
                    .IsRequired()
                    .HasMaxLength(4)
                    .IsUnicode(false);

                entity.HasOne(d => d.IdNavigation)
                    .WithMany(p => p.Favourites)
                    .HasForeignKey(d => d.Id)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK__Favourite__ID__04AFB25B");

                entity.HasOne(d => d.PhoneNavigation)
                    .WithMany(p => p.Favourites)
                    .HasForeignKey(d => d.Phone)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK__Favourite__Phone__05A3D694");
            });

            modelBuilder.Entity<Food>(entity =>
            {
                entity.ToTable("Food");

                entity.Property(e => e.Id).HasColumnName("ID");

                entity.Property(e => e.IdType).HasColumnName("ID_Type");

                entity.Property(e => e.Image)
                    .IsRequired()
                    .HasMaxLength(255);

                entity.Property(e => e.Ingredients).HasMaxLength(255);

                entity.Property(e => e.IsAvailable)
                    .IsRequired()
                    .HasMaxLength(3)
                    .IsUnicode(false);

                entity.Property(e => e.NameFood)
                    .IsRequired()
                    .HasMaxLength(255);

                entity.HasOne(d => d.IdTypeNavigation)
                    .WithMany(p => p.Foods)
                    .HasForeignKey(d => d.IdType)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK__Food__ID_Type__72910220");
            });

            modelBuilder.Entity<FoodType>(entity =>
            {
                entity.ToTable("FoodType");

                entity.Property(e => e.Id).HasColumnName("ID");

                entity.Property(e => e.Image)
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.Property(e => e.NameType)
                    .IsRequired()
                    .HasMaxLength(255);
            });

            modelBuilder.Entity<Receipt>(entity =>
            {
                entity.ToTable("Receipt");

                entity.Property(e => e.Id).HasColumnName("ID");

                entity.Property(e => e.CreateDate).HasColumnType("date");

                entity.Property(e => e.IdRestaurant).HasColumnName("ID_Restaurant");

                entity.Property(e => e.IdVouncher)
                    .IsRequired()
                    .HasMaxLength(255)
                    .HasColumnName("ID_Vouncher");

                entity.Property(e => e.IsAccepted)
                    .IsRequired()
                    .HasMaxLength(4);

                entity.Property(e => e.Note)
                    .IsRequired()
                    .HasMaxLength(255);

                entity.Property(e => e.PhoneClient)
                    .IsRequired()
                    .HasMaxLength(20)
                    .HasColumnName("Phone_Client");

                entity.HasOne(d => d.IdRestaurantNavigation)
                    .WithMany(p => p.Receipts)
                    .HasForeignKey(d => d.IdRestaurant)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK__Receipt__ID_Rest__793DFFAF");

                entity.HasOne(d => d.IdVouncherNavigation)
                    .WithMany(p => p.Receipts)
                    .HasForeignKey(d => d.IdVouncher)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK__Receipt__ID_Voun__7B264821");

                entity.HasOne(d => d.PhoneClientNavigation)
                    .WithMany(p => p.Receipts)
                    .HasForeignKey(d => d.PhoneClient)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK__Receipt__Phone_C__7A3223E8");
            });

            modelBuilder.Entity<ReceiptDetail>(entity =>
            {
                entity.HasKey(e => new { e.Id, e.IdReceipt })
                    .HasName("PK__ReceiptD__77C656C642C776C8");

                entity.ToTable("ReceiptDetail");

                entity.Property(e => e.Id).HasColumnName("ID");

                entity.Property(e => e.IdReceipt).HasColumnName("ID_Receipt");

                entity.HasOne(d => d.IdNavigation)
                    .WithMany(p => p.ReceiptDetails)
                    .HasForeignKey(d => d.Id)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK__ReceiptDetai__ID__7E02B4CC");

                entity.HasOne(d => d.IdReceiptNavigation)
                    .WithMany(p => p.ReceiptDetails)
                    .HasForeignKey(d => d.IdReceipt)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK__ReceiptDe__ID_Re__7EF6D905");
            });

            modelBuilder.Entity<Restaurant>(entity =>
            {
                entity.ToTable("Restaurant");

                entity.Property(e => e.Id).HasColumnName("ID");

                entity.Property(e => e.Address)
                    .IsRequired()
                    .HasMaxLength(255);

                entity.Property(e => e.IdCity).HasColumnName("ID_City");

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasMaxLength(255);

                entity.Property(e => e.Password)
                    .IsRequired()
                    .HasMaxLength(255);

                entity.Property(e => e.Phone)
                    .IsRequired()
                    .HasMaxLength(20);

                entity.HasOne(d => d.IdCityNavigation)
                    .WithMany(p => p.Restaurants)
                    .HasForeignKey(d => d.IdCity)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK__Restauran__ID_Ci__6FB49575");
            });

            modelBuilder.Entity<Vouncher>(entity =>
            {
                entity.ToTable("Vouncher");

                entity.Property(e => e.Id)
                    .HasMaxLength(255)
                    .HasColumnName("ID");

                entity.Property(e => e.Description)
                    .IsRequired()
                    .HasMaxLength(255);
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
