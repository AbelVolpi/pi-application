package com.projetointegrador.pi_application.core.di

// Use cases use @Inject constructor and are provided directly by Hilt.
// No manual @Provides needed — removing @Singleton ensures use cases
// are unscoped (stateless, as they should be).
