// user.model.ts
export interface User {
  id: string | number;
  email: string;
  name: string;
  role: string;
  avatar?: string;
  createdAt?: Date;
  lastLogin?: Date;
}

// You can also define additional types related to users
export interface UserCredentials {
  email: string;
  password: string;
  rememberMe?: boolean;
}

export interface UserRegistration extends UserCredentials {
  name: string;
  confirmPassword: string;
}

export enum UserRole {
  ADMIN = 'admin',
  EDITOR = 'editor',
  USER = 'user',
  GUEST = 'guest'
}

// Example of extending the base User interface for different needs
export interface UserProfile extends User {
  firstName?: string;
  lastName?: string;
  phone?: string;
  address?: {
    street?: string;
    city?: string;
    state?: string;
    zip?: string;
    country?: string;
  };
  preferences?: {
    notifications: boolean;
    newsletter: boolean;
    theme: 'light' | 'dark' | 'system';
  };
}
