package com.example.expensestracker.network

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {

    val client = createSupabaseClient(
        supabaseUrl = "https://qvdxpdzmxrpgplwpiluh.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InF2ZHhwZHpteHJwZ3Bsd3BpbHVoIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDk2NzAwMDYsImV4cCI6MjA2NTI0NjAwNn0.5ZcuwGFndLWobmBBBZT8vUvpBGfP3khxTrC5QgGMiG0"
    ) {
        install(GoTrue)
        install(Postgrest)
    }
}
